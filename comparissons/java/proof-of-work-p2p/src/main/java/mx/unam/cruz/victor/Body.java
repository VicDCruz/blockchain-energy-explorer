package mx.unam.cruz.victor;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder()
@Data
public class Body {
    private final String hash;
    private final long millis;
    private final int nonce;
    private final String id;
    private final long sentAt;
}
