package com.example.aem.core.models;

import com.example.aem.core.services.LinkProcessor;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ExampleComponentWithLinks {

    @Self
    private ResourceResolver resourceResolver;

    @OSGiService
    private LinkProcessor linkProcessor;

    @ValueMapValue
    private String link;

    private String processedLink;

    @PostConstruct
    protected void init() {
        // Assume the link needs to be processed for the publish environment
        processedLink = linkProcessor.processLink(resourceResolver, link, true);
    }

    public String getProcessedLink() {
        return processedLink;
    }
}
