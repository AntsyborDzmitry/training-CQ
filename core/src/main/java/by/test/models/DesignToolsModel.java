package by.test.models;

import java.util.List;
import javax.inject.Inject;
import javax.annotation.PostConstruct;
import by.test.Utils.PathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables = Resource.class)
public class DesignToolsModel {

    @Inject
    @Optional
    private String headerTitle;

    @Inject
    @Optional
    private String headerLink;

    @Inject
    @Optional
    @Default(booleanValues = false)
    private boolean showHeader;

    @Inject
    @Optional
    private List<DesignToolsLinksModel> navigationLinks;

    private String headerUrl;

    private boolean visible;

    private boolean visibleHeader;

    private boolean navigationLinkPresent;

    @PostConstruct
    protected void init() throws Exception {
        navigationLinkPresent = navigationLinks != null;
        visibleHeader = showHeader && StringUtils.isNotBlank(headerTitle);
        visible = visibleHeader || checkLinkModelVisibility(navigationLinks);
        headerUrl = PathUtil.enrichWithExtension(headerLink);
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getHeaderLink() {
        return headerLink;
    }

    public void setHeaderLink(String headerLink) {
        this.headerLink = headerLink;
    }

    public boolean getVisibleHeader() {
        return visibleHeader;
    }

    public boolean getShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public boolean getVisible() {
        return visible;
    }

    public List<DesignToolsLinksModel> getNavigationLinks() {
        return navigationLinks;
    }

    private boolean checkLinkModelVisibility(List<DesignToolsLinksModel> linksModels){
        if (linksModels != null) {
            for (DesignToolsLinksModel model : linksModels){
                if(StringUtils.isNotBlank(model.getLinkTitle())){
                    return true;
                }
            }
        }
        return false;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public boolean getNavigationLinkPresent() {
        return navigationLinkPresent;
    }
}
