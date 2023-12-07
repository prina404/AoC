from collections import defaultdict


def handleJollys(cardDict):
    keys = list(cardDict.keys())
    keys.remove("1")
    keys.sort(key=lambda x: cardDict[x], reverse=True)  # Order by occurrences
    bestKeys = [x for x in keys if x == keys[0]]  # find multiple keys of top occurrencies
    bestKeys.sort(reverse=True)  # Best key first
    cardDict[bestKeys[0]] += cardDict["1"]  # transfer J counts to bestKey
    del cardDict["1"]


def getHandScore(hand: str) -> int:
    hand = hand.split()[0]
    cardDict = defaultdict(int)
    for card in hand:
        cardDict[card] += 1

    if 5 in cardDict.values():  # FiveOfaKind
        return 6
    if "1" in hand:
        handleJollys(cardDict)

    if 5 in cardDict.values():  # FiveOfaKind
        return 6
    if 4 in cardDict.values():  # FourOfAKind
        return 5
    if 3 in cardDict.values() and 2 in cardDict.values():  # FullHouse
        return 4
    if 3 in cardDict.values():  # threeOfAKind
        return 3
    if len(cardDict) == 3:  # TwoPair
        return 2
    if len(cardDict) == 4:  # OnePair
        return 1
    return 0  # HighCard


def solve(cards: list[str]) -> int:
    cards.sort()
    cards.sort(key=lambda x: getHandScore(x))
    return sum([(i + 1) * int(card.split()[1]) for i, card in enumerate(cards)])


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    cards = [   # replacing letters for easier sorting
        x.replace("A", "Z").replace("K", "Y").replace("Q", "X").replace("J", "W") 
        for x in lines
    ]
    print(f"part1: {solve(cards)}")
    print(f"part2: {solve([x.replace('W','1') for x in cards])}")
