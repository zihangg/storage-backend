package zh.backend.utils.paging;

public interface Pageable<T> {
    Page<T> getPage(PageRequest pageRequest);
    int getTotalCount();
}
