package org.oneProjectOneMonth.lms.feature.social.domain.repository;

import org.oneProjectOneMonth.lms.feature.social.domain.model.SocialLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialLinkRepository extends JpaRepository<SocialLink, Long> {
}
