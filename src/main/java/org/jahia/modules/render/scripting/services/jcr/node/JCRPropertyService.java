package org.jahia.modules.render.scripting.services.jcr.node;

import org.jahia.modules.render.scripting.services.jcr.AbstractJCRService;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;

/**
 * Created by smomin on 2/22/16.
 */
public class JCRPropertyService extends AbstractJCRService {
    private final static Logger LOGGER = LoggerFactory.getLogger(JCRPropertyService.class);
    private JCRNodeWrapper node;
    private String name;
    private boolean inherited;

    public JCRPropertyService(final RenderContext renderContext,
                              final Resource currentResource,
                              final String languageCode,
                              final JCRNodeWrapper node,
                              final String name,
                              final boolean inherited) {
        super(renderContext, currentResource, languageCode);
        this.node = node;
        this.name = name;
        this.inherited = inherited;
    }

    @Override
    public Object doProcess() {
        JCRNodeWrapper curNode = node;
        while (true) {
            try {
                if (curNode.hasProperty(name)) {
                    final Property property = curNode.getProperty(name);
                    if (property != null) {
                        if (property.getDefinition().isMultiple()) {
                            return property.getValues();
                        } else {
                            return property.getValue();
                        }
                    }
                } else {
                    if (!inherited) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Property : {} not defined in node {}", name, node.getPath());
                        }
                    } else {
                        try {
                            if ("/".equals(curNode.getPath())) {
                                return "";
                            }
                            curNode = curNode.getParent();
                        } catch (ItemNotFoundException e2) {
                            if (LOGGER.isDebugEnabled()) {
                                LOGGER.debug("Property {} not found in parent nodes {}", name, node.getPath());
                            }
                        } catch (AccessDeniedException e1) {
                            if (LOGGER.isDebugEnabled()) {
                                LOGGER.debug("Property {} parent access denied {}", name, node.getPath());
                            }
                        } catch (RepositoryException e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                    }
                }
            } catch (ConstraintViolationException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Property : " + name + " not found in node " + node.getPath());
                }
            } catch (NoSuchNodeTypeException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Property : " + name + " not found in node " + node.getPath());
                }
            } catch (PathNotFoundException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Property : " + name + " not found in node " + node.getPath());
                }
            } catch (ValueFormatException e) {
                LOGGER.error(e.getMessage(), e);
            } catch (RepositoryException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
