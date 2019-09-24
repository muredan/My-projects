----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/03/2019 10:13:03 AM
-- Design Name: 
-- Module Name: ID - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use ieee.std_logic_unsigned.all;
use ieee.std_logic_arith.all;


-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity ID is
port(
clk: in std_logic;
instruction: in std_logic_vector(15 downto 0) ;
RegDst: in std_logic;
RegWr: in std_logic;
wd: in std_logic_vector (15 downto 0);
wa: in std_logic_vector(2 downto 0);
rd1: out std_logic_vector (15 downto 0);
rd2: out std_logic_vector (15 downto 0);
ext_im: out std_logic_vector(15 downto 0);
wa_out: out std_logic_vector(2 downto 0);
sa: out std_logic;
func: out std_logic_vector(2 downto 0)
);
end ID;

architecture Behavioral of ID is
    type reg_array is array(0 to 7) of std_logic_vector(15 downto 0);
    signal reg_file:reg_array:=(others=>x"0000");
    signal wa_aux:  std_logic_vector (2 downto 0);
    signal ra1:  std_logic_vector (2 downto 0);
    signal ra2: std_logic_vector (2 downto 0);
begin

    ra1<=instruction(12 downto 10);
    ra2<=instruction(9 downto 7);
    wa_aux<=instruction(6 downto 4) when RegDst='1' else instruction(9 downto 7);
    ext_im<="111111111" & instruction(6 downto 0) when instruction(6)='1' else
         "000000000" & instruction(6 downto 0);
    sa<=instruction(3);
    func<=instruction(2 downto 0);
    wa_out<=wa_aux;

    process(clk)
    begin
    if falling_edge(clk) then
        if RegWr='1' then 
                reg_file(conv_integer(wa))<=wd;
        end if;
    end if;
    end process;
    
    rd1<=reg_file(conv_integer(ra1));
    rd2<= reg_file(conv_integer(ra2));
    
end Behavioral;
