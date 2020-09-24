package mk.learning.services;

import lombok.extern.slf4j.Slf4j;
import mk.learning.dao.OAuthClientDetails;
import mk.learning.repository.OAuthClientDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CustomClientDetailsService implements ClientDetailsService {

    @Autowired
    private OAuthClientDetailsRepo oAuthClientDetailsRepo;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        if (!oAuthClientDetailsRepo.existsById(clientId)) {
            throw new UsernameNotFoundException(clientId);
        }
        OAuthClientDetails clientDetails = oAuthClientDetailsRepo.findById(clientId).get();
        BaseClientDetails baseClientDetails = new BaseClientDetails(clientDetails.getClientId(), clientDetails.getResourceIds(), null, clientDetails.getAuthorizedGrantTypes(), clientDetails.getAuthorities());
        baseClientDetails.setClientSecret(clientDetails.getClientSecret());
        baseClientDetails.setScope(convertStringToList(clientDetails.getScope()));
        log.info("baseClientDetails: {}", baseClientDetails);
        log.info("grant types: {}", baseClientDetails.getAuthorizedGrantTypes());
        return baseClientDetails;
    }

    private List<String> convertStringToList(String input) {
        String[] inputArray = input.split(",");
        List<String> result = new ArrayList<>();
        for(int i=0; i<inputArray.length; i++) {
            result.add(inputArray[i].trim());
        }
        return result;
    }

    private List<GrantedAuthority> extractAuthorities(String authorities) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        Arrays.asList(authorities.split(",")).forEach(authority -> {
            authorityList.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return authority;
                }
            });
        });
        return authorityList;
    }
}
