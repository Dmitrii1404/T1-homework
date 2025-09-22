package my.project.accountProcessing.entity.card;

import jakarta.persistence.*;
import lombok.*;
import my.project.accountProcessing.entity.account.Account;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cards",
    indexes = {
            @Index(name = "idx_cards_cardid", columnList = "card_id")
    })
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "card_id", nullable = false, unique = true)
    private String cardId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_system", nullable = false)
    private PaymentSystem paymentSystem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status;

}