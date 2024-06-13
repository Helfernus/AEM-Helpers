package com.example.aem.core.services;

import com.day.cq.commons.Externalizer;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = LinkProcessor.class)
public class LinkHandler {

    @Reference
    private Externalizer externalizer;

    /**
     * Processes a link to add .html extension for internal links and externalize it.
     *
     * @param resourceResolver the ResourceResolver to resolve resources
     * @param link the original link
     * @param isPublish whether the link should be externalized for publish
     * @return the processed link
     */
    public String processLink(ResourceResolver resourceResolver, String link, boolean isPublish) {
        if (StringUtils.isBlank(link)) {
            return link;
        }

        // Check if the link is external
        if (isExternalLink(link)) {
            return link;
        }

        // Append .html extension if it's an internal link and doesn't already have it
        if (!link.endsWith(".html")) {
            link = link + ".html";
        }

        // Externalize the link based on environment (author or publish)
        if (isPublish) {
            return externalizer.publishLink(resourceResolver, link);
        } else {
            return externalizer.authorLink(resourceResolver, link);
        }
    }

    /**
     * Checks if the link is an external link.
     *
     * @param link the link to check
     * @return true if the link is external, false otherwise
     */
    private boolean isExternalLink(String link) {
        return link.startsWith("http://") || link.startsWith("https://") || link.startsWith("//");
    }
}
