import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet{

    private List<Folder> folders;

    private Stream<Folder> allFolders(Folder folder) {
        if (folder instanceof MultiFolder multi) {
            return Stream.concat(
                    Stream.of(folder),
                    multi.getFolders().stream().flatMap(fol -> allFolders(fol)));
        }
        return Stream.of(folder);
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return folders.stream()
                .flatMap(this::allFolders)
                .filter(f -> f.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return folders.stream()
                .flatMap(this::allFolders)
                .filter(f -> f.getSize().equals(size))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) folders.stream()
                .flatMap(this::allFolders)
                .count();

    }
}
