package cn.mu00.tools.blog.domain.vo;

import lombok.Data;

@Data
public class DropCatalogNode {

    private String nodeId;

    private String targetNodeId;

    /**
     * before
     * after
     * inner
     */
    private String dropType;
}
