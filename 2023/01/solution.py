digits = ["one", "two", "three", "four", "five", "six", "seven", "eight","nine"]

def part1(input: str) -> int:
    numbers = []
    for line in input:
        for char in line:
            if char.isdigit():
                fstNum = 10*int(char)
                break
        for char in reversed(line):
            if char.isdigit():
                numbers.append(fstNum + int(char))
                break
    return sum(numbers)

def getInt(data: str):
    if len(data) == 1:
        return int(data)
    return digits.index(data) + 1

def part2(input: str) -> int:
    numbers = []
    for line in input:
        nums = []
        for i in range(len(line)):
            if line[i].isdigit():
                nums.append((i, line[i]))
                continue
            for digit in digits:
                if line[i:].startswith(digit):
                    nums.append((i, digit))
                    
        nums.sort(key=lambda x: x[0])
        fstElem = nums[0][1]
        sndElem = nums[-1][1]

        numbers.append(10 * getInt(fstElem) + getInt(sndElem))
        
    return sum(numbers)


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        lines = f.readlines()
    print(part1(lines))
    print(part2(lines))