package com.tigerpay.payment.event;

import java.util.UUID;

public record AccountCreatedEvent(UUID uuid,
                                  String phoneNumber) {
}
