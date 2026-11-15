package com.saferill.zenstream.sync2.engine

import com.saferill.zenstream.sync.crypto.SyncBytes
import com.saferill.zenstream.sync2.crypto.MemberSecrets
import com.saferill.zenstream.sync2.data.LinkedDatabase
import com.saferill.zenstream.sync2.data.toEntries
import com.saferill.zenstream.sync2.linking.deviceNames
import com.saferill.zenstream.sync2.roster.Settlement
import com.saferill.zenstream.sync2.roster.entryHash
import com.saferill.zenstream.sync2.roster.settleRoster
import com.saferill.zenstream.sync2.store.ChangeList

/** One side of a contested roster: keeping it keeps [device], which removed [removes]. */
data class ContestSide(
    val entry: String,
    val device: String,
    val removes: List<String>,
)

/** The sides the user chooses between while the roster is contested (spec/protocol.md, Contested rosters). */
suspend fun contestSides(
    db: LinkedDatabase,
    store: ChangeList,
    device: MemberSecrets,
): List<ContestSide> {
    val groups = db.groups()
    val record = groups.group() ?: throw LinkedException.NotLinked()
    val applied = groups.roster().toEntries()
    val pins = groups.rosterPins().toSet()
    store.usePins(pins)
    val settled = settleRoster(record.groupId, applied, Session.viewOf(store, record, applied), pins, store.linear)
    if (settled !is Settlement.Contested) return emptyList()
    val names = deviceNames(Session.onApplied(db, store, device))

    fun name(did: String) = names[did]?.name ?: did.take(8)
    return settled.sides.map { side ->
        ContestSide(entryHash(side), name(side.signer), side.remove.orEmpty().map(::name))
    }
}
