package org.anne.zombideck.data

val allCards: List<Card> = listOf(
    // ─── BASE (IDs 1–40) ────────────────────────────────────────────────
    Card(
        1,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(1, 2, 4, 6)
    ),
    Card(
        2,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(2, 3, 5, 7)
    ),
    Card(
        3,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(3, 5, 7, 9)
    ),
    Card(
        4,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(4, 6, 8, 10)
    ),
    Card(
        5,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(1, 2, 4, 6)
    ),
    Card(
        6,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(2, 3, 5, 7)
    ),
    Card(
        7,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(3, 5, 7, 9)
    ),
    Card(
        8,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(4, 6, 8, 10)
    ),
    Card(
        9,
        CardType.SPAWN,
        ZombieType.FATTY,
        listOf(1, 1, 2, 3)
    ),
    Card(
        10,
        CardType.SPAWN,
        ZombieType.FATTY,
        listOf(2, 3, 4, 4)
    ),
    Card(
        11,
        CardType.RUSH,
        ZombieType.FATTY,
        listOf(0, 1, 2, 3)
    ),
    Card(
        12,
        CardType.RUSH,
        ZombieType.FATTY,
        listOf(1, 2, 3, 4)
    ),
    Card(
        13,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(0, 1, 2, 3)
    ),
    Card(
        14,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(1, 1, 2, 3)
    ),
    Card(
        15,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(1, 2, 3, 4)
    ),
    Card(
        16,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(2, 3, 4, 4)
    ),
    Card(
        17,
        CardType.SPAWN,
        ZombieType.ABOMINATION,
        listOf(0, 1, 1, 1)
    ),
    Card(
        18,
        CardType.SPAWN,
        ZombieType.ABOMINATION,
        listOf(0, 1, 1, 1)
    ),
    Card(
        19,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(2, 4, 6, 8)
    ),
    Card(
        20,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(3, 5, 7, 9)
    ),
    Card(
        21,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(4, 6, 8, 10)
    ),
    Card(
        22,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(6, 8, 10, 12)
    ),
    Card(
        23,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(2, 4, 6, 8)
    ),
    Card(
        24,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(3, 5, 7, 9)
    ),
    Card(
        25,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(4, 6, 8, 10)
    ),
    Card(
        26,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(6, 8, 10, 12)
    ),
    Card(
        27,
        CardType.SPAWN,
        ZombieType.FATTY,
        listOf(1, 2, 3, 4)
    ),
    Card(
        28,
        CardType.SPAWN,
        ZombieType.FATTY,
        listOf(3, 4, 5, 6)
    ),
    Card(
        29,
        CardType.RUSH,
        ZombieType.FATTY,
        listOf(1, 2, 3, 4)
    ),
    Card(
        30,
        CardType.RUSH,
        ZombieType.FATTY,
        listOf(2, 3, 4, 5)
    ),
    Card(
        31,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(1, 2, 3, 4)
    ),
    Card(
        32,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(1, 2, 3, 4)
    ),
    Card(
        33,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(2, 3, 4, 5)
    ),
    Card(
        34,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(3, 4, 5, 6)
    ),
    Card(
        35,
        CardType.SPAWN,
        ZombieType.ABOMINATION,
        listOf(1, 1, 1, 1)
    ),
    Card(
        36,
        CardType.SPAWN,
        ZombieType.ABOMINATION,
        listOf(1, 1, 1, 1)
    ),
    Card(
        37,
        CardType.EXTRA_ACTIVATION,
        ZombieType.WALKER,
        listOf(0, 1, 1, 1)
    ),
    Card(
        38,
        CardType.EXTRA_ACTIVATION,
        ZombieType.WALKER,
        listOf(0, 1, 1, 1)
    ),
    Card(
        39,
        CardType.EXTRA_ACTIVATION,
        ZombieType.FATTY,
        listOf(0, 1, 1, 1)
    ),
    Card(
        40,
        CardType.EXTRA_ACTIVATION,
        ZombieType.RUNNER,
        listOf(0, 1, 1, 1)
    ),
    // ─── FORT HENDRIX (IDs 41–80) ──────────────────────────────────────────────
    Card(
        41,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(1, 2, 4, 6),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        42,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(2, 3, 5, 7),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        43,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(3, 5, 7, 9),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        44,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(4, 6, 8, 10),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        45,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(1, 2, 4, 6),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        46,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(2, 3, 5, 7),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        47,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(3, 5, 7, 9),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        48,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(4, 6, 8, 10),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        49,
        CardType.SPAWN,
        ZombieType.FATTY,
        listOf(1, 1, 2, 3),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        50,
        CardType.SPAWN,
        ZombieType.FATTY,
        listOf(2, 3, 4, 4),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        51,
        CardType.RUSH,
        ZombieType.FATTY,
        listOf(0, 1, 2, 3),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        52,
        CardType.RUSH,
        ZombieType.FATTY,
        listOf(1, 2, 3, 4),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        53,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(0, 1, 2, 3),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        54,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(1, 1, 2, 3),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        55,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(1, 2, 3, 4),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        56,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(2, 3, 4, 4),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        57,
        CardType.SPAWN,
        ZombieType.ABOMINATION,
        listOf(0, 1, 1, 1),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        58,
        CardType.SPAWN,
        ZombieType.ABOMINATION,
        listOf(0, 1, 1, 1),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        59,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(2, 4, 6, 8),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        60,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(3, 5, 7, 9),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        61,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(4, 6, 8, 10),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        62,
        CardType.SPAWN,
        ZombieType.WALKER,
        listOf(6, 8, 10, 12),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        63,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(2, 4, 6, 8),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        64,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(3, 5, 7, 9),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        65,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(4, 6, 8, 10),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        66,
        CardType.RUSH,
        ZombieType.WALKER,
        listOf(6, 8, 10, 12),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        67,
        CardType.SPAWN,
        ZombieType.FATTY,
        listOf(1, 2, 3, 4),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        68,
        CardType.SPAWN,
        ZombieType.FATTY,
        listOf(3, 4, 5, 6),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        69,
        CardType.RUSH,
        ZombieType.FATTY,
        listOf(1, 2, 3, 4),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        70,
        CardType.RUSH,
        ZombieType.FATTY,
        listOf(2, 3, 4, 5),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        71,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(1, 2, 3, 4),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        72,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(1, 2, 3, 4),
        true,
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        73,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(2, 3, 4, 5),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        74,
        CardType.SPAWN,
        ZombieType.RUNNER,
        listOf(3, 4, 5, 6),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        75,
        CardType.SPAWN,
        ZombieType.ABOMINATION,
        listOf(1, 1, 1, 1),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        76,
        CardType.SPAWN,
        ZombieType.ABOMINATION,
        listOf(1, 1, 1, 1),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        77,
        CardType.EXTRA_ACTIVATION,
        ZombieType.WALKER,
        listOf(0, 1, 1, 1),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        78,
        CardType.EXTRA_ACTIVATION,
        ZombieType.WALKER,
        listOf(0, 1, 1, 1),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        79,
        CardType.EXTRA_ACTIVATION,
        ZombieType.FATTY,
        listOf(0, 1, 1, 1),
        expansion = Expansion.FORT_HENDRIX
    ),
    Card(
        80,
        CardType.EXTRA_ACTIVATION,
        ZombieType.RUNNER,
        listOf(0, 1, 1, 1),
        expansion = Expansion.FORT_HENDRIX
    ),
    // ─── DANNY TREJO (IDs 81–86) ────────────────────────────────────────────────
    Card(
        81,
        CardType.SPAWN,
        ZombieType.TREJO,
        listOf(),
        expansion = Expansion.DANNY_TREJO
    ),
    Card(
        82,
        CardType.SPAWN,
        ZombieType.TREJO,
        listOf(),
        expansion = Expansion.DANNY_TREJO
    ),
    Card(
        83,
        CardType.SPAWN,
        ZombieType.TREJO,
        listOf(),
        expansion = Expansion.DANNY_TREJO
    ),
    Card(
        84,
        CardType.RUSH,
        ZombieType.TREJO,
        listOf(),
        expansion = Expansion.DANNY_TREJO
    ),
    Card(
        85,
        CardType.RUSH,
        ZombieType.TREJO,
        listOf(),
        expansion = Expansion.DANNY_TREJO
    ),
    Card(
        86,
        CardType.RUSH,
        ZombieType.TREJO,
        listOf(),
        expansion = Expansion.DANNY_TREJO
    )
)