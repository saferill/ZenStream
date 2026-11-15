package com.saferill.zenstream.discord

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DiscordFossIsolationTest {
    @Test
    fun `foss classpath excludes functional Discord implementation`() {
        val forbiddenClasses = listOf(
            "com.saferill.zenstream.discord.DiscordTokenStore",
            "com.saferill.zenstream.discord.DiscordAuthTokens",
            "com.saferill.zenstream.discord.DiscordPlaybackSource",
            "com.saferill.zenstream.discord.DiscordPresenceCoordinator",
            "com.saferill.zenstream.discord.KizzyDiscordPresenceTransport",
            "com.saferill.zenstream.discord.KizzyGatewayProtocol",
        )

        forbiddenClasses.forEach { className ->
            assertThat(runCatching { Class.forName(className) }.isFailure).isTrue()
        }
    }

    @Test
    fun `foss runtime reports Discord unavailable`() {
        assertThat(DiscordPresenceRuntime.settingsState.value.isAvailable).isFalse()
        assertThat(DiscordPresenceRuntime.settingsState.value.isEnabled).isFalse()
        assertThat(DiscordPresenceRuntime.settingsState.value.summary)
            .isEqualTo(DiscordSettingsSummary.UNAVAILABLE)
    }
}
