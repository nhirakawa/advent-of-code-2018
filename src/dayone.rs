pub mod dayone {

    use std::collections::HashSet;
    use std::fs::File;
    use std::io::prelude::*;
    use std::io::{self, BufReader};

    pub fn do_part_one() -> io::Result<()> {
        let f = File::open("day-1-input.txt")?;
        let f = BufReader::new(f);

        let mut sum = 0;
        for line in f.lines() {
            sum += line.unwrap().parse::<i32>().unwrap();
        }

        println!("{}", sum);

        Ok(())
    }

    pub fn do_part_two() -> io::Result<()> {}

    fn read_input() -> std::result::Result<BufReader<File>, io::Error> {
        let f = File::open("day-1-input.txt")?;
        Ok(BufReader::new(f))
    }
}
