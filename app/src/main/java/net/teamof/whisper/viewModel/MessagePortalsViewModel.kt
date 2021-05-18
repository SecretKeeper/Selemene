package net.teamof.whisper.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.teamof.whisper.models.MessagePortal

class MessagePortalsViewModel : ViewModel() {

    private val _messagePortals = MutableLiveData(
        listOf(
            MessagePortal(
                1,
                "Jaina Proudmoore",
                "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
                "Something in your eye?",
                "Just now",
                5
            ),
            MessagePortal(
                2,
                "Lichking",
                "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
                "Something in your eye?",
                "27mins",
                2
            ),
            MessagePortal(
                3,
                "Sylvanas Windrunner",
                "https://s1.1zoom.me/b5050/962/Elves_World_of_WarCraft_Battle_of_Azeroth_Face_574335_2560x1440.jpg",
                "Something in your eye?",
                "12:59",
                0
            ),
            MessagePortal(
                4,
                "Alexstratsa",
                "https://static.wikia.nocookie.net/fairytailfanon/images/e/e3/Febrilia.jpg/revision/latest?cb=20131104020846",
                "Something in your eye?",
                "12:59",
                0
            ),
            MessagePortal(
                5,
                "Anduin Wrynn",
                "https://pbs.twimg.com/media/D8Rdu9wXYAEK-j6.jpg",
                "Something in your eye?",
                "12:59",
                0
            ),
            MessagePortal(
                6,
                "Tyrande Whisperwind",
                "https://image4.uhdpaper.com/wallpaper-mobile-hd/tyrande-whisperwind-world-of-warcraft-night-elf-uhdpaper.com-hd-mobile-4.428.jpg",
                "Something in your eye?",
                "12:59",
                0
            ),
            MessagePortal(
                7,
                "Valeera",
                "https://image4.uhdpaper.com/wallpaper-mobile-hd/valeera-sanguinar-world-of-warcraft-blood-elf-uhdpaper.com-hd-mobile-4.429.jpg",
                "Something in your eye?",
                "12:59",
                0
            ),
            MessagePortal(
                8,
                "Thrall",
                "https://image3.uhdpaper.com/wallpaper-mobile-hd/thrall-world-of-warcraft-battle-for-azeroth-orc-uhdpaper.com-hd-mobile-3.978.jpg",
                "Something in your eye?",
                "12:59",
                0
            ),
            MessagePortal(
                9,
                "Illidan Stormrage",
                "https://image3.uhdpaper.com/wallpaper-mobile-hd/illidan-stormrage-world-of-warcraft-uhdpaper.com-hd-mobile-3.2702.jpg",
                "Something in your eye?",
                "12:59",
                0
            ),
            MessagePortal(
                10,
                "Duel",
                "https://a-static.besthdwallpaper.com/world-of-warcraft-wow-valeera-sanguinar-wallpaper-55768_L.jpg",
                "Something in your eye?",
                "12:59",
                0
            ),
            MessagePortal(
                11,
                "Garrosh Hellscream",
                "https://i.pinimg.com/originals/28/a3/b7/28a3b7ced21793e17d1b9f5d5f1f3fd0.png",
                "Something in your eye?",
                "12:59",
                0
            ),
            MessagePortal(
                12,
                "Liadrin",
                "https://i.pinimg.com/originals/e1/81/25/e181259a1cf944de6dd8cb4d40142185.jpg",
                "Something in your eye?",
                "12:59",
                0
            ),
            MessagePortal(
                13,
                "Maiev",
                "https://images.hdqwalls.com/download/heroes-of-the-storm-maiev-shadowsong-2020-4k-w5-1125x2436.jpg",
                "Something in your eye?",
                "12:59",
                0
            )
        )
    )

    val messages: LiveData<List<MessagePortal>> = _messagePortals
}