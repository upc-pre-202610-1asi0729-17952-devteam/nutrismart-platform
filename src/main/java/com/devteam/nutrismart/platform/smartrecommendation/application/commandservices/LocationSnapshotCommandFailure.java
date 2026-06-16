package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

public sealed interface LocationSnapshotCommandFailure
        permits LocationSnapshotCommandFailure.InvalidData {

    record InvalidData(String reason) implements LocationSnapshotCommandFailure {}
}
