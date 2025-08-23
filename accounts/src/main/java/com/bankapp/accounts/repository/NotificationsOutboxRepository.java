package com.bankapp.accounts.repository;

import com.bankapp.accounts.entity.NotificationsOutbox;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsOutboxRepository extends JpaRepository<NotificationsOutbox, Long> {

    List<NotificationsOutbox> findAllByReceivedFalse();

}
