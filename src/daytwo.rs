
use std::iter::FromIterator;
use std::collections::HashSet;
use multiset::HashMultiSet;
use std::fs::File;
use std::io::{self, BufReader, BufRead};
use itertools::Itertools;

pub fn do_part_one() -> io::Result<()> {
    let lines_vec = read_input()?;

    let mut twos = HashSet::new();
    let mut threes = HashSet::new();
    for line in &lines_vec {
        let mut multiset: HashMultiSet<char> = HashMultiSet::new();
        for c in line.chars() {
            multiset.insert(c);
        }

        for element in multiset.distinct_elements() {
            if multiset.count_of(*element) == 2 {
                twos.insert(line);
            }

            if multiset.count_of(*element) == 3 {
                threes.insert(line);
            }
        }
    }

    println!("[day 2-1] checksum {}", twos.len() * threes.len());

    Ok(())
}

pub fn do_part_two() -> io::Result<()> {
    let lines_vec = read_input()?;

    let products = lines_vec.iter().cartesian_product(lines_vec.iter());

    for (str1, str2) in products {
        let str_set_1: Vec<char> = Vec::from_iter(str1.chars());
        let str_set_2: Vec<char> = Vec::from_iter(str2.chars());

        let zip = str_set_1.iter().zip(str_set_2.iter());

        let mut common_chars = Vec::new();
        for (char1, char2) in zip {
            if char1 == char2 {
                common_chars.push(char1);
            }
        }

        if common_chars.len() == str1.len() - 1 {
            let common_string: String = common_chars.into_iter().collect();
            println!("[day 2-2] common characters {}",common_string);
            break;
        }
    }

    Ok(())
}

fn read_input() -> io::Result<Vec<String>> {
    let f = File::open("inputs/day-two-input.txt")?;
    Ok(Vec::from_iter(BufReader::new(f).lines().flat_map(|s| s)))
}