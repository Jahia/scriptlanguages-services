package org.jahia.modules.render.scripting.services.jcr.node;

import org.jahia.modules.render.scripting.services.ScriptingSupportService;
import org.jahia.services.content.JCRNodeWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;

/**
 * Created by smomin on 2/22/16.
 */
public class JCRNodeVersionService extends ScriptingSupportService {
    private final static Logger LOGGER = LoggerFactory.getLogger(JCRNodeVersionService.class);

    private JCRNodeWrapper node;
    private String versionName;

    /**
     * @param node
     * @param versionName
     */
    public JCRNodeVersionService(final JCRNodeWrapper node,
                                 final String versionName) {
        this.node = node;
        this.versionName = versionName;
    }

    /**
     * @return
     */
    @Override
    public JCRNodeWrapper doProcess() {
        try {
            return (JCRNodeWrapper) node.getVersionHistory().getVersion(versionName);
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
