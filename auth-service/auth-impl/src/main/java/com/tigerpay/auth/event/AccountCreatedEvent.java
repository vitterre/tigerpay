package com.tigerpay.auth.event;

import java.util.UUID;

public record AccountCreatedEvent(UUID uuid,
                                  String phoneNumber) {
}
