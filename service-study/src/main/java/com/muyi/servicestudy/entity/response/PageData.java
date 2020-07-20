package com.muyi.servicestudy.entity.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageData<T> {
    private List<T> items;
    private Page page;

    public PageData() {
    }

    private PageData(List<T> items) {
        this.items = items;
    }

    private PageData(List<T> items, IPage iPage) {
        Page page = new Page();
        page.setPage_start(iPage.getCurrent());
        page.setPage_number(iPage.getSize());
        page.setPage_count(iPage.getPages());

        this.items = items;
        this.page = page;
    }

    public static <T> PageData<T> fillPage(List<T> items) {
        return new PageData(items);
    }

    public static <T> PageData<T> fillPage(IPage iPage) {
        return new PageData(iPage.getRecords(), iPage);
    }
}
