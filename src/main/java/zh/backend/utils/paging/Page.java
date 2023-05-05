package zh.backend.utils.paging;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Page<T> {
    private final List<T> content;
    private final int currentPage;
    private final int totalPages;
}
