package com.example.aem.core.services;

import com.day.cq.commons.Externalizer;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Set;

@Component(service = LinkHandler.class)
public class LinkHandler {

    @Reference
    private Externalizer externalizer;

    @Reference
    private SlingSettingsService slingSettingsService;

    /**
     * Processes a link to add .html extension for internal links and externalize it.
     *
     * @param resourceResolver the ResourceResolver to resolve resources
     * @param link the original link
     * @return the processed link
     */
    public String processLink(ResourceResolver resourceResolver, String link) {
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

        // Determine if the current run mode is publish
        boolean isPublish = isPublishRunMode();

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

    /**
     * Determines if the current run mode is publish.
     *
     * @return true if the current run mode is publish, false otherwise
     */
    private boolean isPublishRunMode() {
        Set<String> runModes = slingSettingsService.getRunModes();
        return runModes.contains("publish");
    }
}
