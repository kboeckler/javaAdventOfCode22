package com.github.kboeckler.adventOfCode22.solution;

import com.github.kboeckler.adventOfCode22.Day;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Day(7)
public class Day7 implements Solution {

  @Override
  public Serializable solvePart1(String[] input) {
    List<Command> commands = parseInput(input);
    Bash bash = new Bash();
    commands.forEach(command -> command.executeOn(bash));
    return bash.getFilesystem().getAllFolders().stream()
        .map(Folder::getSize)
        .filter(size -> size <= 100_000)
        .reduce(0, Integer::sum);
  }

  @Override
  public Serializable solvePart2(String[] input) {
    List<Command> commands = parseInput(input);
    Bash bash = new Bash();
    commands.forEach(command -> command.executeOn(bash));
    int neededSpace = bash.getFilesystem().getRoot().getSize() - 40_000_000;
    return bash.getFilesystem().getAllFolders().stream()
        .map(Folder::getSize)
        .filter(size -> size >= neededSpace)
        .sorted()
        .findFirst()
        .orElse(0);
  }

  private static List<Command> parseInput(String[] input) {
    List<Command> commands = new ArrayList<>();
    for (String row : input) {
      boolean isInput = row.startsWith("$ ");
      if (isInput) {
        commands.add(Command.parse(row.substring(2)));
      } else {
        commands.get(commands.size() - 1).addOutput(row);
      }
    }
    return commands;
  }

  private static class Bash {

    private final Filesystem fs;

    private Folder currentDir;

    private Bash() {
      fs = new Filesystem();
      currentDir = fs.getRoot();
    }

    public void changeDirectory(String target) {
      currentDir =
          currentDir.findTarget(target).orElseGet(() -> fs.makeDirectory(currentDir, target));
    }

    public void listFiles(List<String> listedFiles) {
      listedFiles.stream()
          .filter(line -> !line.startsWith("dir"))
          .forEach(
              line -> {
                String[] split = line.split(" ");
                fs.touch(currentDir, split[1], Integer.parseInt(split[0]));
              });
    }

    public Filesystem getFilesystem() {
      return fs;
    }
  }

  private static class Filesystem {

    private final Folder root = Folder.createRoot();

    public List<Folder> getAllFolders() {
      return root.getAllFolderAsStream().collect(Collectors.toList());
    }

    public Folder getRoot() {
      return root;
    }

    public Folder makeDirectory(Folder parent, String target) {
      Folder folder = Folder.create(parent, target);
      parent.addSubFolder(folder);
      return folder;
    }

    public void touch(Folder parent, String fileName, int fileSize) {
      File file = new File(fileName, fileSize);
      parent.addFile(file);
    }
  }

  private static class Folder {

    private final String name;

    private final Folder parent;

    private final List<Folder> subFolders = new ArrayList<>();

    private final List<File> files = new ArrayList<>();

    private static Folder createRoot() {
      return new Folder(null, "/");
    }

    private static Folder create(Folder parent, String name) {
      return new Folder(parent, name);
    }

    private Folder(Folder parent, String name) {
      this.name = name;
      this.parent = parent;
    }

    public String getName() {
      return name;
    }

    public Optional<Folder> findTarget(String name) {
      if (name.equals("..")) {
        if (parent == null) {
          throw new IllegalArgumentException("Cannot find parent directory. Directory is root.");
        }
        return Optional.of(parent);
      }
      return subFolders.stream().filter(subFolder -> subFolder.getName().equals(name)).findFirst();
    }

    public int getSize() {
      return Stream.concat(
              subFolders.stream().map(Folder::getSize), files.stream().map(File::getSize))
          .reduce(0, Integer::sum);
    }

    public Stream<Folder> getAllFolderAsStream() {
      return Stream.concat(
          subFolders.stream(), subFolders.stream().flatMap(Folder::getAllFolderAsStream));
    }

    public void addSubFolder(Folder folder) {
      subFolders.add(folder);
    }

    public void addFile(File file) {
      files.add(file);
    }
  }

  private static class File {
    private final String name;

    private final int size;

    public File(String name, int size) {
      this.name = name;
      this.size = size;
    }

    public int getSize() {
      return size;
    }
  }

  private static class Command {

    private final CommandExecution execution;

    private final List<String> outputRows = new ArrayList<>();

    private Command(CommandExecution execution) {
      this.execution = execution;
    }

    public static Command parse(String command) {
      String[] split = command.split(" ");
      CommandExecution execution;
      if (split[0].equals("cd")) {
        execution = (Command cmd, Bash bash) -> cmd.changeDirectory(bash, split[1]);
      } else {
        execution = Command::listFiles;
      }
      return new Command(execution);
    }

    public void addOutput(String row) {
      outputRows.add(row);
    }

    public void executeOn(Bash bash) {
      execution.executeOn(this, bash);
    }

    public void changeDirectory(Bash bash, String target) {
      bash.changeDirectory(target);
    }

    public void listFiles(Bash bash) {
      bash.listFiles(outputRows);
    }
  }

  private interface CommandExecution {
    void executeOn(Command command, Bash bash);
  }
}
