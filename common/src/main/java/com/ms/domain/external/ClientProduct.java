package com.ms.domain.external;

import com.ms.enums.Source;
import com.ms.shared.ValueObject;

public interface ClientProduct extends ValueObject<ClientProduct> {

    Source getSource();

}
