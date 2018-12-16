use std::io;
mod dayone;
mod daytwo;

fn main() -> io::Result<()> {
    dayone::dayone::do_part_one()?;
    dayone::dayone::do_part_two()?;

    daytwo::do_part_one()?;

    Ok(())
}
