package by.test.Utils;

import by.test.services.helpers.MySharedConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This class is introduced since native Sightly extension='html' option is failing on URIs like
 * /prodcat/app?page=productSelector&amp;querytype=type=product$$view=table&amp;querycriteria=QBC001=1276$$XJH049^VT-001!0=4
 *
 * @author ignat.sachivko
 *         Date: 21.04.2016
 */
public final class PathUtil {

    private PathUtil() {
        super();
    }

    /**
     * Method adds ".html" postfix to provided internal path if required.
     *
     * @param path internal page path.
     * @return processed page path.
     */
    public static String enrichWithExtension(String path) {
        return enrichWithExtension(path, "html");
    }

    public static String enrichWithExtension(String path, String extension) {
        if (StringUtils.isBlank(path) || StringUtils.isEmpty(extension)) {
            return path;
        }

        boolean hasExtension = hasExtension(path, extension);
        boolean isMailTo = isMailto(path);
        boolean isAnchorLink = isAnchorLink(path);
        boolean hasParameters = hasParameters(path);
        boolean internalResource = isInternalResource(path);

        if (!hasExtension && !isMailTo && !isAnchorLink && !hasParameters && internalResource) {
            return path + "." + extension;
        }

        return path;
    }

    public static boolean hasExtension(String path, String extension) {
        return StringUtils.endsWithIgnoreCase(path, extension);
    }

    public static boolean isInternalResource(String path) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }

        boolean isExternal = checkIsExternal(path);
        boolean isInternal = isInternal(path);
        return !isExternal && isInternal;
    }

    public static String getMappedPath(String path, ResourceResolver resolver){
        if(path == null || resolver ==  null || !resolver.isLive()){
            return null;
        }
        if (checkIsExternal(path)){
            return path;
        }
        String mapped = resolver.map(path);
        try {
            return mapped.startsWith("/") ? mapped : new URL(mapped).getPath();
        } catch (MalformedURLException e) {
            return mapped;
        }
    }

    private static boolean hasParameters(String path) {
        return path.contains("?");
    }

    public static boolean isAnchorLink(String path) {
        return StringUtils.contains(path, "#");
    }

    private static boolean isMailto(final String path) {
        return path.startsWith("mailto:");
    }

    private static boolean isInternal(final String path) {
        return path.startsWith("/");
    }

    public static boolean checkIsExternal(final String path) {
        if (StringUtils.isEmpty(path)){
            return false;
        }
        if (path.startsWith(MySharedConstant.DOUBLE_SLASH)){
            return true;
        }
        try {
            final URI uri = new URI(path);
            return uri.isAbsolute() || uri.isOpaque();
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
