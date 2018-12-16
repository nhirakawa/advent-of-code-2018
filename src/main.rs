use std::io;
mod dayone;

fn main() -> io::Result<()> {
    dayone::dayone::do_day_one()
}
