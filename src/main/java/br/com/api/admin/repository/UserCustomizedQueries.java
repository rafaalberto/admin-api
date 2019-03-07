package br.com.api.admin.repository;

import br.com.api.admin.entity.User;
import br.com.api.admin.resource.user.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserCustomizedQueries extends GenericRepository {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @Transactional(readOnly = true)
    public Page<User> findAll(UserRequest userRequest) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder builderQuery = new StringBuilder();
        builderQuery.append("SELECT u from User u ");

        if (userRequest.getName().isPresent()) {
            buildOperator(builderQuery).append(" lower(u.name) like lower (concat('%', :name, '%'))");
            params.put("name", userRequest.getName().get());
        }

        if (userRequest.getUsername().isPresent()) {
            buildOperator(builderQuery).append(" lower(u.username) like lower (concat('%', :username, '%'))");
            params.put("username", userRequest.getUsername().get());
        }

        if (userRequest.getProfile().isPresent()) {
            buildOperator(builderQuery).append(" u.profile = :profile ");
            params.put("profile", userRequest.getProfile().get());
        }

        Pageable pageable = PageRequest.of(userRequest.getPage().orElse(DEFAULT_PAGE),
                userRequest.getSize().orElse(DEFAULT_SIZE));

        return new PageImpl<>(getQuery(pageable, params, builderQuery).getResultList());
    }

}
