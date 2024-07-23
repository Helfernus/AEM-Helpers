package com.myproject.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, 
       adapters = { HeaderModel.class, ComponentExporter.class }, 
       resourceType = "myproject/components/header", 
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class HeaderModel implements ComponentExporter {

    @ValueMapValue
    private String logo;

    @ValueMapValue
    private String[] navigationLinks;

    public String getLogo() {
        return logo;
    }

    public String[] getNavigationLinks() {
        return navigationLinks;
    }

    @Override
    public String getExportedType() {
        return "myproject/components/header";
    }
}



package com.myproject.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMMode;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.models.factory.ModelFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/myproject/header",
                "sling.servlet.methods=GET"
        })
public class HeaderServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(HeaderServlet.class);

    @Reference
    private transient ModelFactory modelFactory;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        PageManager pageManager = request.getResourceResolver().adaptTo(PageManager.class);
        if (pageManager != null) {
            Page currentPage = pageManager.getContainingPage(request.getResource());
            if (currentPage != null) {
                HeaderModel headerModel = modelFactory.getModelFromWrappedRequest(request, request.getResource(), HeaderModel.class);
                if (headerModel != null) {
                    response.setContentType("application/json");
                    response.getWriter().write(headerModel.getJson());
                } else {
                    LOG.error("Header Model could not be instantiated.");
                    response.sendError(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Header Model could not be instantiated.");
                }
            }
        }
    }
}


