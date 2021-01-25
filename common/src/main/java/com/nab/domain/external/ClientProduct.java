package com.nab.domain.external;

import com.nab.enums.Source;
import com.nab.shared.ValueObject;

public interface ClientProduct extends ValueObject<ClientProduct> {

    Source getSource();

}
