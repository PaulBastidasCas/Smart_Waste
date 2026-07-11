package com.smart_waste.utn.models.views;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VFeedInsigniasUsuarioId implements Serializable {
    private UUID usuId;
    private Integer insId;
}
