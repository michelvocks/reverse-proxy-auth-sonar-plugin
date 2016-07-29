package net.trajano.sonar.plugins.reverseproxyauth;

import java.util.Collections;
import java.util.List;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

/**
 * Plugin entry point.
 */
@Properties({
        @Property(key = "reverseproxyauth.header.name", name = "Header Name", defaultValue = "X-Forwarded-User"),
        @Property(key = "reverseproxyauth.localhost", name = "Hostname to allow Sonar executions", defaultValue = "localhost"),
        @Property(key = "reverseproxyauth.header.groups", name = "Header Groups", defaultValue = "X-Forwarded-Groups"),
        @Property(key = "reverseproxyauth.delimiter", name = "Header Groups Delimiter", defaultValue = "\\|") })
public class ReverseProxyAuthPlugin extends SonarPlugin {
    /**
     * Plugin key.
     */
    public static final String KEY = "reverseproxyauth";

    /**
     * Calls the extension provider.
     * 
     * @return a list containing only the extension provider class.
     */
    @SuppressWarnings("rawtypes")
    @Override
    public List<Class> getExtensions() {
        return Collections.<Class> singletonList(Extensions.class);
    }
}
