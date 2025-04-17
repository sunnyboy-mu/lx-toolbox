package cn.mu00.tools.book.domain;

import cn.mu00.tools.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("book_info")
public class BookInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private String cover;

    private String author;

    private String description;

    private String link;
}
