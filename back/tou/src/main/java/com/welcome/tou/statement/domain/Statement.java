package com.welcome.tou.statement.domain;

import com.welcome.tou.client.domain.Branch;
import com.welcome.tou.client.domain.Worker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "STATEMENT")
public class Statement {

    // 거래명세서 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statement_seq", unique = true, nullable = false)
    private Long statementSeq;

    // 요청 실무자 참조키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "req_worker_seq")
    private Worker reqWorker;

    // 응답 실무자 참조키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_worker_seq")
    private Worker resWorker;

    // 공급 관할구역 참조키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "req_branch_seq", nullable = false)
    private Branch reqBranch;

    // 수급 관할구역 참조키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_branch_seq", nullable = false)
    private Branch resBranch;

    // 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "statement_status", length = 10, nullable = false)
    private StatementStatus statementStatus;

    // 블록 번호
    @Column(name = "block_seq")
    private String blockSeq;

    // 신청일시
    @Column(name = "req_date", nullable = false)
    private LocalDateTime reqDate;

    // 거래일시
    @Column(name = "trade_date")
    private LocalDateTime tradeDate;

    // 응답일시
    @Column(name = "res_date")
    private LocalDateTime resDate;


    public enum StatementStatus {
        PREPARING, WAITING, COMPLETION, REFUSAL, DELETE
    }
}