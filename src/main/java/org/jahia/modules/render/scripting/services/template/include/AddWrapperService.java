package org.jahia.modules.render.scripting.services.template.include;

import org.jahia.modules.render.scripting.services.ScriptingService;
import org.jahia.services.render.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by smomin on 2/17/16.
 */
public class AddWrapperService implements ScriptingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddWrapperService.class);

    private Resource currentResource;
    private String name;

    public AddWrapperService(final Resource currentResource,
                             final String name) {
        this.currentResource = currentResource;
        this.name = name;
    }

    @Override
    public String doProcess() {
        currentResource.pushWrapper(name);
        return "";
    }
}
