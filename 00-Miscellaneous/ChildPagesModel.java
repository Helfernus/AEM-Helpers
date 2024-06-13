package com.example.aem.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ChildPagesModel {

    @Self
    private Resource resource;

    @ValueMapValue
    private String rootPath;

    private List<Page> childPages;

    @PostConstruct
    protected void init() {
        childPages = new ArrayList<>();
        if (rootPath != null && !rootPath.isEmpty()) {
            ResourceResolver resourceResolver = resource.getResourceResolver();
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            if (pageManager != null) {
                Page rootPage = pageManager.getPage(rootPath);
                if (rootPage != null) {
                    Iterator<Page> iterator = rootPage.listChildren();
                    while (iterator.hasNext()) {
                        childPages.add(iterator.next());
                    }
                }
            }
        }
    }

    public List<Page> getChildPages() {
        return childPages;
    }
}
