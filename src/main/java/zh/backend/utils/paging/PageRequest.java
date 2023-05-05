package zh.backend.utils.paging;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageRequest {
    private int pageNumber;
    private int pageSize;
}