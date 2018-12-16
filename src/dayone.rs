pub mod dayone {

    use std::collections::HashSet;
    use std::fs::File;
    use std::io::prelude::*;
    use std::io::{self, BufReader};
    use std::iter::FromIterator;

    pub fn do_part_one() -> io::Result<()> {
        let f = read_input()?;

        let mut sum = 0;
        for line in f.lines() {
            sum += line.unwrap().parse::<i32>().unwrap();
        }

        println!("sum of file is {}", sum);

        Ok(())
    }

    pub fn do_part_two() -> io::Result<()> {
        let f = read_input()?;

        let num_vec = Vec::from_iter(f.lines().flat_map(|s| s).flat_map(|s| s.parse::<i32>()));
        let cycled = num_vec.iter().cycle();

        let mut sum = 0;
        let mut already_seen = HashSet::new();
        for num in cycled {
            sum += num;
            let is_unique = !already_seen.insert(sum);
            if is_unique {
                break;
            }
        }

        println!("first duplicate sum is {}", sum);

        Ok(())
    }

    fn read_input() -> std::result::Result<BufReader<File>, io::Error> {
        let f = File::open("day-1-input.txt")?;
        Ok(BufReader::new(f))
    }
}
