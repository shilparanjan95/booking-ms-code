package entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FlightDetails {


    private Integer id;
    private String airlineName;
    private String source;
    private String destination;
    private String fromDate;
    private String toDate;
    private String fromTime;
    private String toTime;
    private Double price;
    private boolean isNonStop;








}
