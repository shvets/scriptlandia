require File.dirname(__FILE__) + '/../test_helper'

class ContactTypeTest < Test::Unit::TestCase
  fixtures :contact_types

  # Replace this with your real tests.
  def test_truth
    assert_kind_of ContactType, contact_types(:first)
  end
end
