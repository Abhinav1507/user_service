package user_service.userservice.security.Repository;

import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import user_service.userservice.security.models.AuthorizationConsent;

@Repository
public interface AuthorizationConsentRepository extends JpaRepository<AuthorizationConsent, AuthorizationConsent.AuthorizationConsentId> {
	Optional<AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
	void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
