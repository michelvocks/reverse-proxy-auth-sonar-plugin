package net.trajano.sonar.plugins.reverseproxyauth;

import org.sonar.api.config.Settings;
import org.sonar.api.security.ExternalGroupsProvider;
import org.sonar.api.security.DefaultGroups;
import java.util.Collection;
import java.util.List;
import java.util.Arrays;

/**
 * This provides the user details. It is called before
 * {@link org.sonar.api.security.Authenticator#doAuthenticate(org.sonar.api.security.Authenticator.Context)}
 * .
 */
public class ReverseProxyAuthGroupsProvider extends ExternalGroupsProvider {
    /**
     * HTTP Header groups containing the user groups.
     */
    private final String headerGroups;

    /**
     * HTTP Header groups delimiter.
     */
    private final String headerDelimiter;

    /**
     * Constructs the {@link ExternalUsersProvider} with the specified
     * {@link Settings}.
     * 
     * @param settings
     *            settings
     */
    public ReverseProxyAuthGroupsProvider(final Settings settings) {
        super();
        headerGroups = settings.getString("reverseproxyauth.header.groups");
        headerDelimiter = settings.getString("reverseproxyauth.delimiter");
    }

    @Override
    public Collection<String> doGetGroups(final Context context) {
        String headerGroupsValue = context.getRequest().getHeader(
                headerGroups);
        if (headerGroupsValue == null || headerGroupsValue.trim().isEmpty()) {
            headerGroupsValue = DefaultGroups.USERS;
        }
        List<String> groupList = Arrays.asList(headerGroupsValue.split(headerDelimiter));

        // Replace names with sonarqube default group names
        for (int i = 0; i < groupList.size(); i++) {
            if (groupList.get(i).equals("admins")) {
                groupList.set(i, DefaultGroups.ADMINISTRATORS);
            } else {
               groupList.set(i, DefaultGroups.USERS);
            }
        }

        return groupList;
    }
}
