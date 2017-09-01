package by.test.models;


import by.test.Utils.PathUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables=Resource.class)
public class DesignToolsLinksModel {

    @Inject
    @Optional
    private String link;

    @Inject
    @Optional
    private String linkTitle;

    private String url;

    @PostConstruct
    protected void init() throws Exception {
        url = PathUtil.enrichWithExtension(link);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getUrl() {
        return url;
    }

    public boolean isInternal(){
        return PathUtil.isInternalResource(link);
    }

}
