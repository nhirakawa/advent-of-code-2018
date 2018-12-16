
use std::iter::FromIterator;
use std::collections::HashSet;
use multiset::HashMultiSet;
use std::fs::File;
use std::io::{self, BufReader, BufRead};

pub fn do_part_one() -> io::Result<()> {
    let reader = read_input()?;

    let lines_vec = Vec::from_iter(reader.lines().flat_map(|s| s));

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

    println!("[ day2-1] checksum {}", twos.len() * threes.len());

    Ok(())
}

fn read_input() -> io::Result<BufReader<File>> {
    let f = File::open("inputs/day-two-input.txt")?;
    Ok(BufReader::new(f))
}