package com.residea.residea.services;

import com.residea.residea.dto.FormValutazioneRequest;
import com.residea.residea.dto.ValutazioneResultResponse;

public interface CalcoloValutazioneService {
    ValutazioneResultResponse calculateFromRequest(FormValutazioneRequest req);
    ValutazioneResultResponse calculateFromImmobileId(Integer idImmobile);
}
