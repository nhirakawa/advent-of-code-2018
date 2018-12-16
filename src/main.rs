use std::io;
mod dayone;

fn main() -> io::Result<()> {
    dayone::dayone::do_part_one()?;
    dayone::dayone::do_part_two()?;

    Ok(())
}
