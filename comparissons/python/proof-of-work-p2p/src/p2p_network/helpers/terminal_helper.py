from termcolor import colored


def print_colored(text, clr="white", endl=1):
    endline = "\n" * endl

    print(colored(text, clr), end=endline)
