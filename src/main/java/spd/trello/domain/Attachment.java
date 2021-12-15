package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.io.File;
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Attachment extends Resource{
    private String link;
    private String name;
    private File file;

}
