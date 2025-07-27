package com.bankapp.accounts.repository;

import com.bankapp.accounts.entity.NotificationsOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationsOutboxRepository extends JpaRepository<NotificationsOutbox, Long> {

    List<NotificationsOutbox> findAllByReceivedFalse();

}
